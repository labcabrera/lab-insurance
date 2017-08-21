package org.lab.insurance.core.jpa;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.lab.insurance.model.common.ASTNode;

/**
 * Clase que genera a partir de una expresión FIQL un {@link Predicate} de JPA.<br>
 * 
 * @see <a href="http://cxf.apache.org/javadoc/latest/org/apache/cxf/jaxrs/ext/search/FiqlParser.html">CXF FiqlParser</a>
 */
public class FiqlParser {

	public static final String OR = ",";
	public static final String AND = ";";
	public static final String GT = "=gt=";
	public static final String GE = "=ge=";
	public static final String LT = "=lt=";
	public static final String LE = "=le=";
	public static final String EQ = "==";
	public static final String NEQ = "!=";
	public static final String NULL = "=n=";
	public static final String NOT_NULL = "=!n=";

	/**
	 * A los operadores estándar añadimos el operador 'like' para búsquedas de texto.
	 */
	public static final String LK = "=lk=";

	/**
	 * Convierte una expressión FIQL a un {@link ASTNode} a partir del cual podemos recuperar el {@link Predicate} de JPA.
	 * 
	 * @param expr
	 * @param builder
	 * @param root
	 * @return
	 */
	public ASTNode<Predicate> parse(String expr, CriteriaBuilder builder, Root<?> root) {
		List<String> subexpressions = new ArrayList<String>();
		List<String> operators = new ArrayList<String>();
		int level = 0;
		int lastIdx = 0;
		int idx = 0;
		for (idx = 0; idx < expr.length(); idx++) {
			char c = expr.charAt(idx);
			if (c == '(') {
				level++;
			} else if (c == ')') {
				level--;
				if (level < 0) {
					throw new RuntimeException(String.format("Unexpected closing bracket at position %d", idx));
				}
			}
			String cs = Character.toString(c);
			boolean isOperator = AND.equals(cs) || OR.equals(cs);
			if (level == 0 && isOperator) {
				String s1 = expr.substring(lastIdx, idx);
				String s2 = expr.substring(idx, idx + 1);
				subexpressions.add(s1);
				operators.add(s2);
				lastIdx = idx + 1;
			}
			boolean isEnd = idx == expr.length() - 1;
			if (isEnd) {
				String s1 = expr.substring(lastIdx, idx + 1);
				subexpressions.add(s1);
				operators.add(null);
				lastIdx = idx + 1;
			}
		}
		if (level != 0) {
			throw new RuntimeException(String.format("Unmatched opening and closing brackets in expression: %s", expr));
		}
		if (operators.get(operators.size() - 1) != null) {
			String op = operators.get(operators.size() - 1);
			String ex = subexpressions.get(subexpressions.size() - 1);
			throw new RuntimeException("Dangling operator at the end of expression: ..." + ex + op);
		}
		int from = 0;
		int to = 0;
		SubExpression ors = new SubExpression(OR, builder);
		while (to < operators.size()) {
			while (to < operators.size() && AND.equals(operators.get(to))) {
				to++;
			}
			SubExpression ands = new SubExpression(AND, builder);
			for (; from <= to; from++) {
				String subex = subexpressions.get(from);
				ASTNode<Predicate> node = null;
				if (subex.charAt(0) == '(') {
					node = parse(subex.substring(1, subex.length() - 1), builder, root);
				} else {
					node = parseComparison(subex, builder, root);
				}
				ands.add(node);
			}
			to = from;
			if (ands.getSubnodes().size() == 1) {
				ors.add(ands.getSubnodes().get(0));
			} else {
				ors.add(ands);
			}
		}
		if (ors.getSubnodes().size() == 1) {
			return ors.getSubnodes().get(0);
		} else {
			return ors;
		}
	}

	private Comparison parseComparison(String expr, CriteriaBuilder builder, Root<?> root) {
		String comparators = GT + "|" + GE + "|" + LT + "|" + LE + "|" + EQ + "|" + NEQ + "|" + NOT_NULL + "|" + NULL + "|" + LK;
		String s1 = "[\\p{ASCII}]+(" + comparators + ")";
		Pattern p = Pattern.compile(s1);
		Matcher m = p.matcher(expr);
		if (m.find()) {
			String name = expr.substring(0, m.start(1));
			String operator = m.group(1);
			String value = null;
			if (!NOT_NULL.equals(operator) && !NULL.equals(operator)) {
				value = expr.substring(m.end(1));
				if ("".equals(value)) {
					throw new RuntimeException("Not a comparison expression: " + expr);
				}
			}
			return new Comparison(name, operator, value, builder, root);
		} else {
			throw new RuntimeException("Not a comparison expression: " + expr);
		}
	}

	private class SubExpression implements ASTNode<Predicate> {

		private final String operator;
		private final List<ASTNode<Predicate>> subnodes;
		private final CriteriaBuilder builder;

		public SubExpression(String operator, CriteriaBuilder builder) {
			this.operator = operator;
			this.builder = builder;
			this.subnodes = new ArrayList<ASTNode<Predicate>>();
		}

		public void add(ASTNode<Predicate> node) {
			subnodes.add(node);
		}

		public List<ASTNode<Predicate>> getSubnodes() {
			return Collections.unmodifiableList(subnodes);
		}

		@Override
		public String toString() {
			String s = operator.equals(FiqlParser.AND) ? "AND" : "OR";
			s += ":[";
			for (int i = 0; i < subnodes.size(); i++) {
				s += subnodes.get(i);
				if (i < subnodes.size() - 1) {
					s += ", ";
				}
			}
			s += "]";
			return s;
		}

		@Override
		public Predicate build() {
			List<Predicate> predicates = new ArrayList<Predicate>();
			for (Iterator<ASTNode<Predicate>> it = getSubnodes().iterator(); it.hasNext();) {
				ASTNode<Predicate> comp = it.next();
				predicates.add(comp.build());
			}
			Predicate predicate;
			if (OR.equals(operator)) {
				predicate = builder.or(predicates.toArray(new Predicate[0]));
			} else {
				predicate = builder.and(predicates.toArray(new Predicate[0]));
			}
			return predicate;
		}
	}

	private class Comparison implements ASTNode<Predicate> {

		private final String name;
		private final String operator;
		private final Comparable<?> value;
		private final CriteriaBuilder builder;
		private final Root<?> root;

		public Comparison(String name, String operator, Comparable<?> value, CriteriaBuilder builder, Root<?> root) {
			this.name = name;
			this.operator = operator;
			this.value = value;
			this.builder = builder;
			this.root = root;
		}

		@Override
		public String toString() {
			return name + operator + value;
		}

		/**
		 * TODO Buscar una mejor solucion para la conversion de tipos a la hora de generar los predicados.
		 */
		@Override
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Predicate build() {
			Predicate result = null;
			Expression expressionValue = getExpression();
			Class<?> type = expressionValue.getJavaType();
			Comparable targetValue = null;
			if (Number.class.isAssignableFrom(type)) {
				targetValue = new BigDecimal(String.valueOf(value));
			} else if (Date.class.isAssignableFrom(type)) {
				targetValue = parseDate(value);
			} else if (Enum.class.isAssignableFrom(type)) {
				targetValue = Enum.valueOf((Class) type, (String) value);
			} else {
				targetValue = value;
			}
			if (EQ.equals(operator)) {
				result = builder.equal(expressionValue, targetValue);
			} else if (GE.equals(operator)) {
				result = builder.greaterThanOrEqualTo(expressionValue, targetValue);
			} else if (GT.equals(operator)) {
				result = builder.greaterThan(expressionValue, targetValue);
			} else if (LE.equals(operator)) {
				result = builder.lessThanOrEqualTo(expressionValue, targetValue);
			} else if (LT.equals(operator)) {
				result = builder.lessThan(expressionValue, targetValue);
			} else if (NOT_NULL.equals(operator)) {
				result = builder.isNotNull(expressionValue);
			} else if (NULL.equals(operator)) {
				result = builder.isNull(expressionValue);
			} else if (LK.equals(operator)) {
				result = builder.like(expressionValue, "%" + targetValue + "%");
			} else {
				throw new RuntimeException("Not implemented operator: " + operator);
			}
			return result;
		}

		private Date parseDate(Object value) {
			try {
				return value != null ? new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(value)) : null;
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}

		private <T> Expression<T> getExpression() {
			StringTokenizer tokenizer = new StringTokenizer(name, ".");
			Path<T> result = null;
			while (tokenizer.hasMoreElements()) {
				String partial = tokenizer.nextToken();
				if (result == null) {
					result = root.get(partial);
				} else {
					result = result.get(partial);
				}
			}
			return result;
		}
	}
}

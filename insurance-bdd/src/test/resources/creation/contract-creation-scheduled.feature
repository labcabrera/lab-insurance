Feature: basic contract creation
  Scenario: insertar contrato basico
    When Inicializo la base de datos
    When Preparo contrato con acuerdo AM01
    When Establezco como suscriptor del contrato a la persona identificada con 11222333Z
    When Establezco como beneficiario del contrato a la persona identificada con 55666777W
    When Establezco un pago inicial bruto de 100000 euros
    When Establezco la distribucion del pago inicial en (ASSET01:50%; ASSET02: 30%; GUARANTEE01:20%)
    When Establezco la fecha de contratacion a 2017/01/02
    When Muestro el JSON del contrato
    When establezco la fecha del sistema a 2017/01/01
    When Invoco al servicio de contratacion
    When Programo la aprobacion del contrato a fecha 2017/01/11 con el id del contrato
    When Programo la accion de recepcion de pago a fecha 2017/01/15 con el id del contrato
    Then Simulo una ejecucion de 2017/01/11 a 2017/02/01
    And Verifico que el suscriptor es 11222333Z

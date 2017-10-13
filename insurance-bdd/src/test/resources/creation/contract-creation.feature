Feature: basic contract creation
  Scenario: insertar contrato basico
    When purgo las colas de contratacion
    When inicializo la base de datos
    When preparo contrato con acuerdo AM01
    When establezco como suscriptor del contrato a la persona identificada con 11222333Z
    When establezco como beneficiario del contrato a la persona identificada con 55666777W
    When establezco un pago inicial bruto de 100000 euros
    When establezco la distribucion del pago inicial en (ASSET01:50%; ASSET02:30%; GUARANTEE01:20%)
    When establezco la fecha de contratacion a 2017/01/02
    When muestro el JSON del contrato
    When establezco la fecha del sistema a 2017/01/01
    When invoco al servicio de contratacion
    When programo la aprobacion del contrato a fecha 2017/01/11 con el id del contrato
    When programo la accion de recepcion de pago a fecha 2017/01/20 con el id del contrato
    
    Then espero hasta que el estado de contrato sea "INITIAL" (timeout: 10sg)
    Then espero hasta que el estado del pago inicial sea "INITIAL" (timeout: 10sg)
    
    Then echo "#########################################################################"
    Then echo "# Iterando hasta la aprobacion del contrato"
    Then echo "#########################################################################"
    
    Then simulo una ejecucion de 2017/01/11 a 2017/01/11
    Then espero hasta que el estado de contrato sea "APPROVED" (timeout: 10sg)
    Then verifico que el suscriptor es 11222333Z
    
    Then echo "#########################################################################"
    Then echo "# Iterando hasta la recepcion del pago inicial"
    Then echo "#########################################################################"

    Then simulo una ejecucion de 2017/01/12 a 2017/02/25
    Then espero hasta que el estado de contrato sea "STARTED" (timeout: 10sg)
    Then espero hasta que el estado del pago inicial sea "ACCOUNTED" (timeout: 120sg)
	
	
	
    #Then espero que se vacie la cola "portfolio-create"
    #Then espero 600 segundos hasta que el estado del pago inicial sea "PROCESSED"
    #Then espero que se vacie la cola "initial-payment-reception"
	#Then espero 600 segundos hasta que el estado del pago inicial sea "ACCOUNTED"
    #Then verifico que el estado del contrato es "STARTED"

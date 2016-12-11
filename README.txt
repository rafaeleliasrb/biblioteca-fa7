Configurações Postgres:
	1) Criar database 'biblioteca'
	
Configurações WildFly:
	1) Adicionar um Module para o banco de dados Postgres:
		-Criar pasta {diretório}\wildfly-10.0.0.Final\modules\org\postgres\main
		 e adicionar os arquivos o .jar da versão do Postgres e module.xml:
		 	<?xml version="1.0" encoding="UTF-8"?>
			<module xmlns="urn:jboss:module:1.1" name="org.postgres">
				<resources>
					<resource-root path="{nomeDoJar}"/>
				</resources>
			
				<dependencies>
					<module name="javax.api"/>
					<module name="javax.transaction.api"/>
					<module name="javax.servlet.api" optional="'true"/>
				</dependencies>
			</module>
	2) No arquivo {diretório}\wildfly-10.0.0.Final\standalone\configuration\standalone.xml
	   adicionar o drive do Postgres nos <drivers>:
		   	<driver name="postgres" module="org.postgres">
		        <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
		    </driver>
	    
	   e adicionar o datasource bibliotecaFa7-ds nos <datasources>:
	   		<datasource jndi-name="java:/bibliotecaFa7-ds" pool-name="bibliotecaFa7DS" enabled="true" use-java-context="true">
            	<connection-url>jdbc:postgresql://{nomeHost}:{porta}/biblioteca</connection-url>
                <driver>postgres</driver>
                <pool>
                    <min-pool-size>5</min-pool-size>
                    <initial-pool-size>5</initial-pool-size>
                    <max-pool-size>100</max-pool-size>
                    <prefill>true</prefill>
                </pool>
                <security>
                    <user-name>{nomeDoUsuario}</user-name>
                    <password>{senhaDoUsuario}</password>
                </security>
            </datasource>

Configurações ActiveMQ:
	1) Url: tcp://localhost:61616
	2) Criar fila: biblioteca-queue
	3) Criar fila: distribuidora-queue

OBSERVAÇÃO: Antes de subir o WildFly é necessário que o Banco de dados 'biblioteca' 
e o ActiveMQ com a fila 'biblioteca-queue' estejam rodando.
require 'jar_dependencies'
JBUNDLER_LOCAL_REPO = Jars.home
JBUNDLER_JRUBY_CLASSPATH = []
JBUNDLER_JRUBY_CLASSPATH.freeze
JBUNDLER_TEST_CLASSPATH = []
JBUNDLER_TEST_CLASSPATH.freeze
JBUNDLER_CLASSPATH = []
JBUNDLER_CLASSPATH << (JBUNDLER_LOCAL_REPO + '/org/hibernate/hibernate-core/4.3.7.Final/hibernate-core-4.3.7.Final.jar')
JBUNDLER_CLASSPATH << (JBUNDLER_LOCAL_REPO + '/org/hibernate/common/hibernate-commons-annotations/4.0.5.Final/hibernate-commons-annotations-4.0.5.Final.jar')
JBUNDLER_CLASSPATH << (JBUNDLER_LOCAL_REPO + '/org/jboss/logging/jboss-logging-annotations/1.2.0.Beta1/jboss-logging-annotations-1.2.0.Beta1.jar')
JBUNDLER_CLASSPATH << (JBUNDLER_LOCAL_REPO + '/antlr/antlr/2.7.7/antlr-2.7.7.jar')
JBUNDLER_CLASSPATH << (JBUNDLER_LOCAL_REPO + '/org/jboss/jandex/1.1.0.Final/jandex-1.1.0.Final.jar')
JBUNDLER_CLASSPATH << (JBUNDLER_LOCAL_REPO + '/org/javassist/javassist/3.18.1-GA/javassist-3.18.1-GA.jar')
JBUNDLER_CLASSPATH << (JBUNDLER_LOCAL_REPO + '/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar')
JBUNDLER_CLASSPATH << (JBUNDLER_LOCAL_REPO + '/org/hibernate/javax/persistence/hibernate-jpa-2.1-api/1.0.0.Final/hibernate-jpa-2.1-api-1.0.0.Final.jar')
JBUNDLER_CLASSPATH << (JBUNDLER_LOCAL_REPO + '/mysql/mysql-connector-java/5.1.26/mysql-connector-java-5.1.26.jar')
JBUNDLER_CLASSPATH << (JBUNDLER_LOCAL_REPO + '/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar')
JBUNDLER_CLASSPATH << (JBUNDLER_LOCAL_REPO + '/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.0.0.Final/jboss-transaction-api_1.2_spec-1.0.0.Final.jar')
JBUNDLER_CLASSPATH << (JBUNDLER_LOCAL_REPO + '/org/jboss/logging/jboss-logging/3.1.3.GA/jboss-logging-3.1.3.GA.jar')
JBUNDLER_CLASSPATH.freeze

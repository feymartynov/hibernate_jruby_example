JAVA_BUILD_PATH = './lib/java/build'
$CLASSPATH << JAVA_BUILD_PATH

Dir["#{JAVA_BUILD_PATH}/**/*.class"].reject { |f| f[/\$/] }.each do |file|
  package_name = File.dirname(file).match(Regexp.new("#{JAVA_BUILD_PATH}/(.*)"))[1].gsub('/', '.')
  class_name = File.basename(file, '.class')
  java_import([package_name, class_name].join('.'))
end

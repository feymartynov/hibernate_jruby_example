namespace :java do
  desc 'compile java classes'
  task :compile do
    require './.jbundler/classpath'
    classpath =  ".:#{ENV['MY_RUBY_HOME']}/lib/jruby.jar:" + JBUNDLER_CLASSPATH.join(':')

    Dir['./lib/java/**/*.java'].each do |file|
      puts "Compiling #{file}"
      puts `CLASSPATH=#{classpath} javac -sourcepath ./lib/java/src -d ./lib/java/build #{file} -Xlint:deprecation 2>&1`
    end
  end
end

namespace :tests do
  desc 'run specs with trace'
  task :trace do
    puts `JAVA_OPTS="-Djava.util.logging.config.file=logging.properties" rspec`
  end
end

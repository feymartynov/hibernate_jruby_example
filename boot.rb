require 'java'
require 'jruby/core_ext'

# JAVA_OPTS="-Djava.util.logging.config.file=logging.properties" rspec
$CLASSPATH << File.dirname(__FILE__)

require 'rubygems'
require 'bundler'
Bundler.require

require 'active_support'
require 'active_support/dependencies'
ActiveSupport::Dependencies.autoload_paths += %w[lib domain/models]

require 'lib/db'

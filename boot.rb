require 'java'
require 'jruby/core_ext'

require 'rubygems'
require 'bundler'
Bundler.require

require 'load_java'

require 'active_support'
require 'active_support/dependencies'
ActiveSupport::Dependencies.autoload_paths += %w[lib domain/models]

require 'lib/db'

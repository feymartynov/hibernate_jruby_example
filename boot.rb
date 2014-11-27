require 'rubygems'
require 'bundler'
Bundler.require

require 'java'
require 'jruby/core_ext'

require 'active_support'
require 'active_support/dependencies'
ActiveSupport::Dependencies.autoload_paths += %w[domain/models]

require 'db'

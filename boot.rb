require 'rubygems'
require 'bundler'
Bundler.require

require 'java'

require 'active_support'
require 'active_support/dependencies'
ActiveSupport::Dependencies.autoload_paths += %w[domain/models]

require 'java_map'
require 'db'

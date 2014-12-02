require 'boot'

RSpec.configure do |config|
  config.color = true

  config.after(:all) do
    DB.shutdown
  end
end

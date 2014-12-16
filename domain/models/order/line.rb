class Order::Line
  include Virtus.model
  include Equalizer.new :title, :price, :position

  attribute :title, String
  attribute :price, Float, default: 0
  attribute :position, Integer

  become_java!(false)
end

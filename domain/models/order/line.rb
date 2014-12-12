class Order::Line
  include Virtus.model
  include Equalizer.new :title, :price, :position

  attribute :title, String
  attribute :price, Integer
  attribute :position, Integer

  become_java!(false)
end

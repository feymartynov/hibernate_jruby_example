class Order::Line
  include Virtus.model

  attribute :title, String
  attribute :price, Integer
  attribute :position, Integer

  become_java!(false)
end

class Order::Line
  include Virtus.value_object

  # attribute :title, String
  attribute :price, Integer
  attribute :position, Integer

  become_java!(false)
end

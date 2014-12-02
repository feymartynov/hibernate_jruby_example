class Order::Line < BaseModel
  attribute :title, String
  attribute :price, Float
  attribute :position, Integer
end

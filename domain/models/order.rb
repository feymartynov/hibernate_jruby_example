class Order < BaseModel
  attribute :lines, ['Order::Line']
end

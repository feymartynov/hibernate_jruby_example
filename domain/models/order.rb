class Order < BaseModel
  attribute :lines, ['Order::Line']

  def add_line(attrs = {})
    attrs.merge!(position: lines.size)
    lines << Order::Line.new(attrs)
  end
end

class Order
  include Virtus.model

  attribute :id, Integer
  attribute :total, Integer, default: 0
  # attribute :status, String, default: 'draft'
  # attribute :lines, ['Order::Line']
  # attribute :created_at, Time
  # attribute :deleted, Boolean, default: false

  def add_line(attrs = {})
    attrs.merge!(position: lines.size)
    lines << Order::Line.new(attrs)
  end

end

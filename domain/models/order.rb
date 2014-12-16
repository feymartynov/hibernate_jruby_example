class Order
  include Virtus.model

  attribute :id, Integer
  attribute :total, Float, default: 0
  attribute :status, Symbol, default: :draft
  attribute :lines, ['Order::Line']
  attribute :created_at, Time
  attribute :deleted, Boolean, default: false
  attribute :meeting_place, String
  attribute :special_instructions, String

  def add_line(attrs = {})
    attrs.merge!(position: lines.size)
    line = Order::Line.new(attrs)
    self.total += line.price
    lines << line
  end

  become_java!(false)
end

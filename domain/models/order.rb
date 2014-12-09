class Order
  include Virtus.model

  attribute :id, Integer
  attribute :status, String, default: 'draft'
  attribute :lines, ['Order::Line']
  attribute :created_at, Time

  def add_line(attrs = {})
    attrs.merge!(position: lines.size)
    lines << Order::Line.new(attrs)
  end

  become_java!(false)
end

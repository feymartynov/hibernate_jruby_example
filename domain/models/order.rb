class Order
  include Virtus.model

  attribute :id, Integer
  attribute :lines, ['Order::Line']

  def add_line(attrs = {})
    attrs.merge!(position: lines.size)
    lines << Order::Line.new(attrs)
  end

  become_java!(false)
end

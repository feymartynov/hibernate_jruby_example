require 'boot'

order = Order.new
order.lines << Order::Line.new(title: 'product', price: 100)
order.lines << Order::Line.new(title: 'another product', price: 200)

DB.save(order)
exit
require 'boot'

order = Order.new
order.add_line(title: 'product', price: 100)
order.add_line(title: 'another product', price: 200)

DB.save(order)

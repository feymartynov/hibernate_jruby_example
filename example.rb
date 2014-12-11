require 'boot'

order = Order.new(total: 300)
order.add_line(title: 'product', price: 100)
order.add_line(title: 'another product', price: 200)

DB.save(order)
DB.shutdown

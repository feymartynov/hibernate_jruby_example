require 'boot'

order = Order.new(meeting_place: 'somewhere',
                  special_instructions: "don't forget")

order.add_line(title: 'product', price: 100)
order.add_line(title: 'another product', price: 200)

id = DB.save(order)
loaded_order = DB.get(Order, id)
DB.shutdown
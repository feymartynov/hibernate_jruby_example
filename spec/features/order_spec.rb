require 'spec_helper'

describe 'order saving' do
  let(:order) do
    order = Order.new(meeting_place: 'somewhere',
                      special_instructions: "don't forget")

    order.add_line(title: 'product', price: 100)
    order.add_line(title: 'another product', price: 200)
    
    order
  end

  let(:order_id) { DB.save(order) }
  subject(:saved_order) { DB.get(Order, order_id) }

  it 'should persist the order' do
    expect(saved_order.id).to eq(order.id)
  end

  it 'should persist order lines' do
     expect(saved_order.lines).to eq(order.lines)
  end
end

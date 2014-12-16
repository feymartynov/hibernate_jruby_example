require 'spec_helper'

describe 'order saving' do
  let(:order) do
    order = Order.new(meeting_place: 'somewhere',
                      special_instructions: "don't forget")

    order.add_line(title: 'product', price: 100)
    order.add_line(title: 'another product', price: 200)
    
    order
  end

  subject(:saved_order) do
    DB.save(order)
    DB.get(Order, order.id)
  end

  subject(:resaved_order) { DB.get(Order, order.id) }

  it 'should persist the order' do
    expect(saved_order.id).to eq(order.id)
  end

  it 'should persist order lines' do
     expect(saved_order.lines).to eq(order.lines)
  end

  it 'should update the order' do
    saved_order.status = :completed
    DB.save(saved_order)
    expect(resaved_order.status).to eq(:completed)
  end

  it 'should update lines' do
    saved_order.lines.first.title = 'renamed line'
    DB.save(saved_order)
    expect(resaved_order.lines.first.title).to eq('renamed line')
  end

  it 'should delete lines' do
    saved_order.lines.pop
    DB.save(saved_order)
    expect(resaved_order.lines.size).to eq(1)
  end
end

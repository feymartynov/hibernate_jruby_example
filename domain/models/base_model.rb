class BaseModel
  include Virtus.model
  include JavaMap

  attribute :id, Integer

  def ==(other_model)
    other_model.attributes == attributes
  end
end

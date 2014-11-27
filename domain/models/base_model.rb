class BaseModel
  include java.util.Map
  include Virtus.model

  attribute :id, Integer

  # Java::Map implementation

  # Removes all mappings from this Map (optional operation).
  def clear
    key_set.each { |key| put(key, nil) }
  end

  # Returns true if this Map contains a mapping for the specified key.
  def contains_key(key)
    key_set.include?(key)
  end

  # Returns true if this Map maps one or more keys to the specified value. More formally,
  def contains_value(value)
    key_set.any? { |key| get(key) == value }
  end

  # Returns a Set view of the mappings contained in this Map
  def entries
    cast_to_java(key_set.map { [key, get(key)] })
  end

  # Compares the specified Object with this Map for equality. Returns true if the given
  def equals(object)
    key_set.all? { |key| object.respond_to?(key) && object.key == get(key) }
  end

  # Returns the value to which this Map maps the specified key. Returns null if the Map
  def get(key)
    cast_to_java(public_send(key))
  end

  # Returns the hash code value for this Map
  def hash_code
    Digest::SHA256.digest(self)
  end

  # Returns true if this Map contains no key-value mappings.
  def is_empty
    key_set.all? { |key| get(key).blank? }
  end

  # Returns a Set view of the keys contained in this Map
  def key_set
    cast_to_java(Set.new(attribute_set.map(&:name)))
  end

  # Associates the specified value with the specified key in this Map (optional operation)
  def put(key, value)
    public_send("#{key}=", value)
  end

  # Copies all of the mappings from the specified Map to this Map (optional operation)
  def put_all(other_map)
    other_map.key_set.each { |key| put(key, other_map.get(key)) }
  end

  # Removes the mapping for this key from this Map if present (optional operation).
  def remove(key)
    put(key, nil)
  end

  # Returns the number of key-value mappings in this Map.
  def size
    key_set.size
  end

  # Returns a Collection view of the values contained in this Map
  def values
    entries
  end

  private

  def cast_to_java(value)
    case value
      when Set, Array then java.util.HashSet.new(value)
      when Hash then java.util.HashMap.new(value)
      when Time then java.util.Date(value)
      else value
    end
  end
end

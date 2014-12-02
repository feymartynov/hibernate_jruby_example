module DB
  def self.transaction
    session = session_factory.get_current_session
    tx = session.begin_transaction
    result = yield(session)
    tx.commit
    result
  rescue
    tx.rollback if tx
    raise
  ensure
    session.close if session && session.is_open
  end

  def self.save(entity)
    transaction { |s| s.save(entity.class.to_s, entity) }
  end

  def self.get(model_class, *args)
    transaction do |session|
      data_set = session.get(model_class.to_s, *args)

      if data_set.is_a?(Java::OrgHibernateCollectionInternal::PersistentSet)
        data_set.to_array.map { |item| model_class.new(item) }
      else
        model_class.new(data_set)
      end
    end
  end

  def shutdown
    session_factory.close
  end

  private

  def self.session_factory
    @session_factory ||= Java::OrgHibernateCfg::Configuration.new.configure.build_session_factory
  end
end

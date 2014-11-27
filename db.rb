module DB
  import 'org.hibernate.cfg.Configuration'
  import 'org.hibernate.SessionFactory'

  def self.transaction
    session = session_factory.get_current_session
    tx = session.begin_transaction
    yield(session)
    tx.commit
  rescue
    tx.rollback if tx
    raise
  ensure
    session.close if session && session.is_open
  end

  def self.save(entity)
    transaction { |s| s.save(entity.class.to_s, entity) }
  end

  private

  def self.session_factory
    @session_factory ||= Configuration.new.configure.build_session_factory
  end
end

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

  def self.save(*args)
    transaction { |s| s.save(*args) }
  end

  def self.get(*args)
    transaction { |s| s.get(*args) }
  end

  private

  def self.session_factory
    @session_factory ||= Java::OrgHibernateCfg::Configuration.new
                             .configure
                             .build_session_factory
                             # .setPersisterClassResolver(Java::OrgHibernaterb::PoroPersisterClassResolver)
  end
end

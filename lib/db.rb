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

  class << self
    %w(save get).each do |method|
      define_method(method) do |*args|
        transaction { |s| s.public_send(method, *args) }
      end
    end
  end

  private

  def self.session_factory
    @session_factory ||= Java::OrgHibernateCfg::Configuration.new.configure.build_session_factory
  end
end

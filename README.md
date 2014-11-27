# `Hibernate` usage example in `jruby`.

This example uses `Hibernate` with `MySQL` backend as DOA to save a sample domain aggregate base on `Virtus` models.

## Requirements
- `jruby-1.7`
- `mysql 5.x`

## Setup and running
Edit `hibernate.cfg.xml` to customize database settings if needed. The do the following:

```
# DB schema setup
mysql hibernate_example -u ep -pq123 < schema.sql

# Install dependencies
bundle install
jbundle install

# Run
jruby ./hibernate_example.rb
```
Then look to the DB and check for the persisted models.

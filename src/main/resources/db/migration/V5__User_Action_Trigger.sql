CREATE OR REPLACE FUNCTION users.users_table_update_notify() RETURNS trigger AS $$
DECLARE
id bigint;
BEGIN
    IF TG_OP = 'INSERT' THEN
        id = NEW.id;
ELSE
        id = OLD.id;
END IF;
    PERFORM pg_notify('user_action', json_build_object('id', id, 'action', TG_OP)::text);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER user_action AFTER INSERT OR DELETE ON users."user"
    FOR EACH ROW EXECUTE PROCEDURE users.users_table_update_notify();
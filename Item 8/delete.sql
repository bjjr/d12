start transaction;

use `Acme-CinemaDB`;

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database `Acme-CinemaDB`;

commit;
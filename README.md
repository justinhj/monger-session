# Forked version by justinhj

I made the following changes:

Rather than serialize data to and from a :content field in the mongo document I just use the regular monger conversion to and from bson

The user can specify a date field, which enables you to use a TTL index which will automatically deletes orphaned sessions

Updated the unit tests

# mongo session store using monger

monger-session use mongodb as a Clojure/Ring's http session storage.

!clojure 1.4 is now required!

## Usage

## License

Copyright (C) 2012 Martin Kremers

Distributed under the GPLv3, see http://www.gnu.org/licenses/gpl.html

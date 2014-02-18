# mongo session store using monger to persist ring sessions to MongoDB

I made the following changes to the original fork from kremers:

Rather than serialize data to and from a :content field in the mongo document I just use the regular monger conversion to and from bson so that the data the user adds to the session is represented normally in MongoDB

The user can specify a date field, which enables you to use a TTL index which will automatically deletes orphaned sessions

Updated the unit tests

## Usage

TODO

## License

Copyright (C) 2012 Martin Kremers

Distributed under the GPLv3, see http://www.gnu.org/licenses/gpl.html

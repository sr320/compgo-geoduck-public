#!/usr/bin/perl

use strict;
use DBI;

# BLAST RESULTS FILE
# IN FORM OF GEODUCK_ID\tUNIPROT_ID\n
my($_BLAST_FILE) = '/home/user/uniprot_blast_map';

# DATABASES INVOLVED
my( $_UNIPROT_DB ) = 'compgo_uniprot';          # from where we get annotations
my( $_GEODUCK_DB ) = 'compgo_geoduck';     	# to where we put annotations

# SET UP OUR DATABASE CONNECTION
my($dbh) = getDBH($_UNIPROT_DB, 3306);

# LOOP THROUGH BLAST RESULTS, FOR EACH MATCH, ADD ALL ANNOTATIONS FROM UNIPROT ($_UNITPROT_DB)
# FOR THE HIT PROTEIN TO THE NEW DATABASE, ASSOCIATING THOSE GO ANNOTATIONS
# WITH THE PROTEIN ACCESSIONS FOUND IN THE EXPERIMENT

open my $fh, '<', $_BLAST_FILE or die "Cannot open $_BLAST_FILE: $!";

while ( my $line = <$fh> ) {
    chomp;
    $line =~ s/\s+$//;

    my @row = split( "\t", $line  );

    my($geod_acc) = $row[ 0 ];
    my($unip_acc) = $row[ 1 ];

    if( !$unip_acc ) { next; }
    if( !$geod_acc ) { die "Invalid line format: $line\n"; }

    my( $sql ) = "INSERT INTO $_GEODUCK_DB.go_annotation SELECT * FROM $_UNIPROT_DB.go_annotation WHERE db_object_id = '$unip_acc'";
    print "$sql\n";

    my( $sth ) = $dbh->prepare( $sql );
    $sth->execute();

    $sql = "UPDATE $_GEODUCK_DB.go_annotation SET db_object_id = '$geod_acc' WHERE db_object_id = '$unip_acc'";
    $sth = $dbh->prepare( $sql );
    $sth->execute();
}

close($fh);


# RETURNS A DATABASE HANDLE WITH THE CORRECT CONNECTION INFORMATION
my(%HANDLES) = ( );
sub getDBH {
   my($trash,$database,$port) = @_;

   if(!$port) { $port = '3306'; }

   if($HANDLES{$database}) { return $HANDLES{$database}; }

   my($driver,$user,$password,$host);
   ($driver,$user,$password,$host) = ('mysql','USERNAME', 'PASSWORD', 'localhost');

   my($dsn) = "DBI:$driver:database=compgo_uniprot:host=$host:port=3306";
   my($DBH) = DBI->connect($dsn, $user, $password, {'RaiseError' => 1, 'PrintError' => 0});

   $HANDLES{$database} = $DBH;
   return $HANDLES{$database};
}
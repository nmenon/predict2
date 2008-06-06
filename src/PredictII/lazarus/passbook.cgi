#!/usr/bin/perl
use CGI ;
$h = new CGI;
print $h->header();

# START GENERATION OF THE PAGE
if ( "$ENV{'REQUEST_METHOD'}" eq  "POST" ){
	$UserName=$h->param('UserName');
   $AccountNo="1212321";
	$output=`cat collection/PassBook.html`;
   $data = `./jmad passbook $UserName`;
   if ($? !=0){
      print "BADLUCK";
      #redict to the error page
   }
   else
   {
   #Replace the UserName
   $output=~ s/<!--USERNAME><--!>/$UserName/g;
   
   #Replcae the Account Number
   $output=~ s/<!--ACNO><--!>/$AccountNo/g;

   #Put in the Data
   $output=~ s/<!--STARTDATA><ENDDATA--!>/$data/g;
   print $output;
   }
}
else
{
   # redirect to the forbidden page
   print" U try to access me invalidly";
}

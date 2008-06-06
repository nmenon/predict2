#!/usr/bin/perl
use CGI ;
$h = new CGI;
print $h->header();

# START GENERATION OF THE PAGE
if ( "$ENV{'REQUEST_METHOD'}" eq  "POST" ){
	$UserName=$h->param('UserName');
   $PassPhrase=$h->param('PassPhrase');
   $AccountNo="1212321";
	$output=`cat collection/main.html`;
   $data = `./jmad auth $UserName "$PassPhrase"`;
   if ($? !=0){
      print "BADLUCK- Data Base Unavailable at the moment..";
      #redict to the error page
   }
   else
   {
      if ($data eq "error"){
         print "Unauthorised Attempt of Storming the castle... Please Go AWAY...";
      }
      else{
            #Authenticated replace the Name
            $output=~ s/"UNKNOWNUSERNAME"/"$UserName"/g;
      
            #Replace the UserName
            $output=~ s/<!--USERNAME><--!>/$UserName/g;
   
            #Replcae the Account Number
            $output=~ s/<!--ACNO><--!>/$AccountNo/g;

            #Put in the Data
            $output=~ s/<!--STARTDATA><ENDDATA--!>/$data/g;
            print $output;
         }
   }
}
else
{
   $i=`cat collection/index.html`;
   print"$i";
}

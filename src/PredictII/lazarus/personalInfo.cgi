#!/usr/bin/perl
use CGI ;
$h = new CGI;
print $h->header();

# START GENERATION OF THE PAGE
if ( "$ENV{'REQUEST_METHOD'}" eq  "POST" ){
	$UserName=$h->param('UserName');
   $PassPhrase=$h->param('PassPhrase');
   $AccountNo="1212321";
	$output=`cat collection/PersonalInfo.html`;
   if ("$PassPhrase" eq ""){
      #Display The Information
      $data = `./jmad personalInfoGet $UserName`;
      @parts=split("--==",$data);
      $Address=$parts[1];
      $MailID=$parts[2];
      $balance=$parts[3];
         #Replace the UserName
         $output=~ s/<!--USERNAME><--!>/$UserName/g;
         $output=~ s/UNKNOWNUSERNAME/$UserName/g;
         $output=~ s/UNKNOWNUSERPASSPHRASE//g;
         $output=~ s/"UNKNOWNADDRESS"/$Address/g;
         $output=~ s/UNKNOWNMAILID/$MailID/g;
         $output=~ s/UNKNOWNBALANCEAMOUNT/$balance/g;
         
         #Replcae the Account Number
        $output=~ s/<!--ACNO><--!>/$AccountNo/g;
        print $output;
         
         
   }
   else
   {
      #Update the Information
      if ($? !=0){
         print "BADLUCK";
         #redict to the error page
      }
      else
      {
         $UserName= $h->param('UserName');
         $Address=$h->param('Address');
         $MailID=$h->param('MailID');
         $PassPhrase=$h->param('PassPhrase');
         print "$Address--- $UserName--- $PassPhrase--- $MailID----";
          $data=`./jaga personalInfoUpdate "$UserName" "$PassPhrase" "$Address" "$MailID" `;
         print "Hello World".$data;
         if ($? != 0){
         print "<h2>Sorry We Have A problem with the DataBase<br>".$data;
         }
         else{
            print "<h1>SuccessFull Updation...";
            print "<br> Please use the Back Arrow to move back<br></h1>";
         #Replace the UserName
         $output=~ s/<!--USERNAME><--!>/$UserName/g;
      
         #Replcae the Account Number
         $output=~ s/<!--ACNO><--!>/$AccountNo/g;

         #Put in the Data
         $output=~ s/<!--STARTDATA><ENDDATA--!>/$data/g;
         }
         print "GoodBye";
      }
   }
}
else
{
   # redirect to the forbidden page
}

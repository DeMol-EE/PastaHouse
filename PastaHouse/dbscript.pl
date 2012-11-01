@ARGV = "postcodes.csv";

$id = 1;
open (MYFILE, '>>data.csv');

while(<>){
($postcode, $naam,$upcase,$fullcode) = split ";";
 print MYFILE join ";",$id, $postcode,$naam;
 print MYFILE "\n";
$id++;

}

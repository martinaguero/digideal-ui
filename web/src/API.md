
### Introduction
The API of **_DigiDeal_** parses high-level (natural) language contracts. The text displayed in the preview is the body of the message that the server receives.
Currently, it can parse the first type of contract (the one created from the form). However, the engine is designed to work with any kind of contract. The length, parties, conditions, currencies of a contract can be configured infinitely, it only requires a [Xtext] compatible grammar. On the next section, the grammar behind the default parser.

&nbsp;
### Grammar
The current contract (in English) grammar is:
```html
Contract:
	'DigiDeal' cid=SERIAL paragraph+=Sentence*;
Sentence:
	sentenceType=STRING | Subject | Reference | PayTo | PaymentCondition | SupervisedBy | Date;
Subject:
	'@' name=ID '{' address=STRING ',' email=Email '}';
Email:
	STRING;
Reference:
	'@' type=[Subject];
PayTo:
	'@' payer=[Subject] 'will pay' ('@' collector=[Subject]) 'the amount of' quantity=STRING 'with tBTC' btc=STRING;
PaymentCondition:
	'if:' ('@' collector=[Subject]) level=FactsLevel facts=Facts 'the following product or service:' description=STRING ;
FactsLevel:
	options=(AnyFact | AllFacts);
AnyFact:
	value='complies with ANY of the following facts:';
AllFacts:
	value='delivers to:';
Facts:
	'{' (fact+=STRING ("," fact+=STRING)*) '}';
SupervisedBy:
	'Operation supervised by the agent' agent=Subject;
Date:
	'Created in:' date=STRING;
terminal SERIAL: '#'('A'..'Z'|'0'..'9')*;
```

If you need a personalized contract with different parties, conditions or attributes, please contact us at: `trimatek.org@gmail.com`

&nbsp;&nbsp;
### Entity
Every new contract, must be send to the engine as instances of the **Source** entity. It attributes are:

<b>Source.json</b>
```
name = UID (provided by http://trimatek.org:8282/digidata/serial)
locale = us
text = (the contract in natural language)
pdf = (byte array of the file to be sended to the parties)
```
&nbsp;

### Call
```
https://<server>:<port>/api/sources/
```

#### Method
```
POST
```

#### Headers
```
Content-Type: application/json
Content-Type: charset=utf-8
Accept: application/json
```

#### Body
Example of an English contract to be processed by the parser:
```
DigiDeal #76RA0T6 
"The following agreement between Mr./Mrs. John Doe hereinafter the buyer, identified by 
" @john { " miA1K5CxBVsWmTrN34TftfWFgDMS5udREf ", " john@gmail.com " } 
" and between Mr./Mrs. Jane Doe hereinafter the seller, identified by " 
@jane { " 2N7FEif7kgh8VZTZ7uwwJbdX1RMX4Q8ZqeX ", " jane@gmail.com " } ",
establishes that " @john will pay @jane the amount of "USD 10" with tBTC "0.00099850" 
if: @jane delivers to: { " 8936 Myrtle St., San Diego, CA " } 
the following product or service: "the product or service description" 
Operation supervised by the agent 
@digideal { " 2MsHiZNZ3FpEPFTXVo7y3cCBKGQmHB37hbo ", " digideal.services@gmail.com " }
Created in: "31/07/2019" 
```

#### Response
If the source can be parsed, the response will be a **201** status code and the string will be `"Compile success"`. If it fails, the response will be a **409** status code and the string will be `"Compile failed"`.
<br />
<br />


### Workflow
If the contract is compiled it means that the parser translated the natural language into instructions. Those instructions will be processed by the bot and will also start the workflow:
```
1.Communicate the parties and request for funds.
2.Wait for the funds.
3.Send the unlock code (QR).
4.Wait for the code.
5.Unlock the funds.
6.Confirm transaction successful to all parties.
```

[Xtext]:https://www.eclipse.org/Xtext/
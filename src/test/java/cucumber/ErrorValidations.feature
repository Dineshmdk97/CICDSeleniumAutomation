@tag
Feature: Title of your feature
  I want to use this template for my feature file

  @ErrorValidations
  Scenario Outline: Validating Error on landing page
    Given I landed on Ecommerce page
    When Logged in with username <name> and password <password>
    Then "Incorrect email or word." message is displayed on LandingPage

    Examples: 
			| name  								| password 	 | productName  	 |
      | dineshkumar@gmail.com | wrongpwd	 | ADIDAS ORIGINAL |


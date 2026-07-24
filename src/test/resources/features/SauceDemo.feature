Feature: Login functionality and add the highest price item to the cart

  As a registered user
  I want to log into the application
  So that I can access my account

  Background:
    Given I go to url 'https://www.saucedemo.com'
  @test
  Scenario: Successful login with valid credentials
    When I enter a valid username 'standard_user'
    And  I enter a valid password 'secret_sauce'
    And  I clicks the login button
    Then I should be redirected to the 'Products' page
    And  I add the highest price item to the cart
    And  I click on the add cart link on the page
    Then I should see the highest price item added to the cart


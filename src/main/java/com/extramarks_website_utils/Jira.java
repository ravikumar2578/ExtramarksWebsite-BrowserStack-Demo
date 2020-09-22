package com.extramarks_website_utils;

import java.util.List;

import org.openqa.selenium.Keys;
import org.testng.Reporter;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.Project;
import net.rcarz.jiraclient.RestClient;

public class Jira {
	private static JiraClient jira;

	public static JiraClient setUpBug() throws JiraException {
		BasicCredentials creds = new BasicCredentials(Constants.JIRA_USERNAME, Constants.JIRA_PASSWORD);
		jira = new JiraClient(Constants.JIRA_URL, creds);
		return jira;
	}

	public static Object createBug() throws JiraException {
		List<Project> ProjectList = jira.getProjects();
		int totalproject = ProjectList.size();
		System.out.println("Total Project :" + totalproject);
		for (int i = 0; i < totalproject; i++) {
			System.out.println("Project : " + i + " " + ProjectList.get(i));
		}
		RestClient restClient = jira.getRestClient();
		// List<CustomFieldOption> allowedValues =
		// jira.getCustomFieldAllowedValues("customfield_10700", "LA", "Story");
		// for (CustomFieldOption customFieldOption : allowedValues) {
		// System.out.println(customFieldOption.getValue());
		// }
		Issue newIssue2 = jira.createIssue(Constants.JIRA_PROJECT, "Bug").field(Field.SUMMARY, "TestData")
				.field("customfield_10700", 0).execute();
		System.out.println("Created Issue : " + newIssue2.toString());
		return newIssue2.toString();
	}

	public static Object getProjectList() throws JiraException {
		BasicCredentials creds = new BasicCredentials(Constants.JIRA_USERNAME, Constants.JIRA_PASSWORD);
		JiraClient jira = new JiraClient(Constants.JIRA_URL, creds);
		List<Project> ProjectList = jira.getProjects();
		int totalproject = ProjectList.size();
		System.out.println("Total Project :" + totalproject);
		// for (int i = 0; i < totalproject; i++) {
		// System.out.println("Project : " + i + " " + ProjectList.get(i));
		// }
		return ProjectList;
	}

	public static Object addBugDetails(String assinee) throws JiraException {
		BasicCredentials creds = new BasicCredentials(Constants.JIRA_USERNAME, Constants.JIRA_PASSWORD);
		JiraClient jira = new JiraClient(Constants.JIRA_URL, creds);

		// Add Assignee , Bug details
		String bugDetails = "Steps are >> "+ Keys.ENTER;
		for (String str : Reporter.getOutput()) {
			bugDetails = str + Keys.ENTER;
		}
		Issue issue = jira.getIssue((String) createBug());
		issue.update().field(Field.DESCRIPTION, bugDetails).field(Field.ASSIGNEE, assinee).execute();
		return true;
	}
}
# Class Scheduler

Note: This file is a work in progress. A few changes also need to be made to make it possible to use different models and files. These changes will be made soon.

# Description

This program uses genetic algorithms to solve the university class scheduling problem.

Note: This program is relatively basic in solving the scheduling program at a large scale. It is not thoroughly tested and may not work fully as expected. It is not meant for real-world use in its current state, and may work better for demonstration purposes. 

# The Problem

In a university, carefully managing class schedules is important. There are a large number of constraints that need to be met for this to happen successfully. Here are some examples:

* Teachers cannot be in 2 classrooms at the same time
* Multiple teachers cannot be in the same classroom at any given time
* Teachers cannot teach multiple sections at the same time
* Each course section must be assigned to one class, time slot (i.e. MWF at 8am), and teacher
* etc.

When trying to work out a class schedule for a large set of classes, students, and teachers, this can get very complicated very quickly.

# A Solution: Genetic Algorithms

One of the ways to solve this problem is to apply a form of artificial intelligence known as genetic algorithms. Genetic algorithms work by arbitrarily generating a series of hypotheses, and then testing the hypotheses against a set of constraints. The method of generation imitates the process of evolution. Each hypothesis, in this case, represents class section assignments. Hypotheses are encoded in the form of bitstrings, where a set of bits (i.e. 010) corresponds to a specific unit of a category (for instance, the string "000010" could correspond to the MWF 8am time slot). Hypotheses breed with one another, sharing patterns of bits. Mutations could occur at any point. During each breeding cycle, a set of hypotheses are generated and evaluated against the constraints. Hypotheses that violate a high number of constraints receive a low fitness value, whereas hypotheses that violate a low number of constraints receive a high fitness value. Hypotheses with low fitness values are pruned (imitating the process of natural selection, "survival of the fittest"), while hypotheses with high fitness values remain and breed with one another for the next cycle. Over time, better combinations of class section assignments remain in children hypotheses, while worse combinations are pruned. This eventually leads to a hypothesis that is "fit enough" to be the final solution.

# Usage

Run the jar file alongside the provided example files to get an idea of what's going on. If you want to use this program with your own data, ensure that your data follows the same format as the example CSVs.

Specific files are required for specific models. Models are methods of solving the problem by using a specific set of inputs and constraints. The specific models this program implements are described in [Calvin Thach's thesis, "The University Class Scheduling Problem"](https://scholarworks.calstate.edu/concern/theses/v979v493k).

The models this program implements are outlined in pages 6-16 of the linked paper. The sections below go over how to use each model.


## Basic Model

Description:
* This is the most fundamental model that each of the following models builds off of, as it defines the fundamental constraints. Additionally, a goal of the basic model is to balance course sections across weekday timeslots. For instance, there should be a roughly equal number of TH classes and MWF classes.

How to use:

Files required:
* Classrooms
* Course sections
* Time slots

## Basic Teacher Model

Description:

How to use:

Files required: 

## Teacher Preference Model

Description:

How to use:

Files required: 

## Teacher Difference Model

Description:

How to use:

Files required: 

## Teacher Satisfaction Model

Description:

How to use:

Files required: 

## Teacher Tricriteria Model

Description:

How to use:

Files required: 

#!/bin/bash

sbt clean coverage test
sbt coverageReport
idea target/scala-3.5.1/scoverage-report/index.html
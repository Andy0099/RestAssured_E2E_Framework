# Step 1: Use a lightweight Maven image with JDK 21
FROM maven:3.9.6-eclipse-temurin-21

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy only the pom.xml first to cache dependencies (Efficiency Trick)
COPY pom.xml .
RUN mvn dependency:go-offline

# Step 4: Copy the rest of your project code
COPY . .

# Step 5: Command to run your TestNG/Cucumber suite
CMD ["mvn", "test"]

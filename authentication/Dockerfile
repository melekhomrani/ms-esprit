# Use official Node LTS for stability
FROM node:20-alpine

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install --production

COPY server.js ./
COPY .env ./

EXPOSE 5050

# Start the app
CMD ["node", "server.js"]

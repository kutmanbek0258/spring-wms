# build stage
FROM node:16-alpine as build-stage

WORKDIR /app

COPY . .

RUN npm install
RUN npm run build

FROM nginx:stable-alpine as deploy-stage

ENV TZ=Asia/Bishkek
RUN mkdir -p /var/log/nginx
RUN mkdir -p /var/www/html
RUN chown nginx:nginx /var/www/html

COPY --from=build-stage /app/dist /var/www/html/wms-client
COPY ./nginx/nginx.conf /etc/nginx/nginx.conf
COPY nginx/wms-client.conf /etc/nginx/conf.d/wms-client.conf

EXPOSE 8080

CMD ["nginx", "-g", "daemon off;"]
import axios from "axios";
const serverURL = import.meta.env.VITE_WMS_SERVICE_URL;

const instance = axios.create({
  baseURL: serverURL,
  headers: {
    "Content-Type": "application/json",
  },
});

export default instance;

// Kira Defteri service worker.
// Önce internetten en güncel sürümü ister; internet yoksa son kaydedilen sürümü açar.
// Böylece GitHub'a yüklenen her yeni sürüm telefona kendiliğinden gelir.
const CACHE = 'kira-defteri';

self.addEventListener('install', () => self.skipWaiting());
self.addEventListener('activate', (e) => e.waitUntil(self.clients.claim()));

self.addEventListener('fetch', (e) => {
  const req = e.request;
  if (req.method !== 'GET') return;
  e.respondWith(
    fetch(req, { cache: 'no-cache' })
      .then((res) => {
        if (res.ok && new URL(req.url).origin === self.location.origin) {
          const copy = res.clone();
          caches.open(CACHE).then((c) => c.put(req, copy));
        }
        return res;
      })
      .catch(() => caches.match(req).then((r) => r || caches.match('./')))
  );
});
